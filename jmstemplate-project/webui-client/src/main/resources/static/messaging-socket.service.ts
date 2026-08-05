// @ts-ignore
import {IMessage, Message} from "@stomp/stompjs";
// @ts-ignore
import {Injectable, Injector} from "@angular/core";
// @ts-ignore
import {RxStompService} from "@stomp/ng2-stompjs";
// @ts-ignore
import {Observable} from "rxjs/internal/Observable";
// @ts-ignore
import {NotificationService, SessionService} from 'webcore-ui';
// @ts-ignore
import {TranslateService} from "@ngx-translate/core";
// @ts-ignore
import {NotificationCategoryToast, ToastNotificationModel} from "app/shared/model/notification.model";
// @ts-ignore
import {Subscription} from "rxjs/internal/Subscription";
// @ts-ignore
import {IAuthSession} from 'webcore-ui/services/session/session.model';
// @ts-ignore
import {DomSanitizer} from "@angular/platform-browser";
// @ts-ignore
import {NotificationBicFileResponse, NotificationResponse} from "app/components/import/data-grid/import-datagrid.model";
// @ts-ignore
import {Subject} from "rxjs/internal/Subject";

@Injectable({
    providedIn: 'root',
})
export class MessagingSocketService {
    private readonly NOTIFICATION_TOPIC = "/topic/notification_workflow";
    private readonly NOTIFICATION_RECORD_STATUS_TOPIC = "/topic/notification_record_status";

    public messages: Observable<Message>;
    private subscription: Subscription;
    private toastNotifReceived$ = new Subject<any>();

    constructor(private stompService: RxStompService,
                private translateService: TranslateService,
                private sessionService: SessionService,
                private injector: Injector,
                private domSanitizer: DomSanitizer) {
    }

    init(): void {
        this.sessionService.getAuthSessionCache().subscribe((authSession: IAuthSession) => {
            const name: string = authSession.user.name;
            this.subscribeToToast(name);
        });
    }

    getToastNotifReceivedObservable(): Observable<any> {
        return this.toastNotifReceived$.asObservable();
    }

    // TODO. 前端通过STOMP Client订阅特定Topic的消息
    private subscribeToToast(username: string): void {
        let queueName = this.NOTIFICATION_TOPIC;
        if (!!username) {
            queueName += '/' + username;
        }
        this.stompService.watch(queueName) // not a Subscription it's a stomp subscribe
            .subscribe(response => {
                try {
                    let notificationResponse: ToastNotificationModel = JSON.parse(response.body);
                    notificationResponse = this.sanitizeToastNotificationModel(notificationResponse);

                    if (notificationResponse.category === NotificationCategoryToast && notificationResponse.messageCode!=='publish.COMPLETED_WITH_SUCCESS') {
                        const messageArgs = notificationResponse.messageArgs;
                        const messageCode = notificationResponse.messageCode;

                        const title = this.translateService.instant('notification.title.' + notificationResponse.eventType, messageArgs);
                        const message = this.translateService.instant('notification.message.' + messageCode, messageArgs);

                        this.toastNotifReceived$.next(notificationResponse);
                        const notificationService = this.injector.get(NotificationService);
                        const optNotifications = {
                            "success": () => notificationService.success(message, title),
                            "error": () => notificationService.error(notificationResponse.errorCode + ': ' + message, title)
                        };
                        optNotifications[notificationResponse.type]();
                    }
                } catch (e) {
                    console.warn("Failed to handle toast notification", e);
                }
            });
    }

    sanitizeToastNotificationModel(notif: ToastNotificationModel): ToastNotificationModel {
        notif.messageCode = this.sanitizeString(notif.messageCode);
        notif.eventType = this.sanitizeString(notif.eventType);
        notif.category = this.sanitizeString(notif.category);
        notif.errorCode = this.sanitizeString(notif.errorCode);
        notif.type = this.sanitizeString(notif.type);
        notif.messageArgs = this.sanitizeObj(notif.messageArgs);
        return notif;
    }

    sanitizeString(msg: any): any {
        msg = this.domSanitizer.sanitize(1, msg);
        return msg;
    }

    sanitizeObj(messageArgsInput: any): any {
        if (!!messageArgsInput) {
            let messageArgs = {};
            Object.keys(messageArgsInput).forEach(key => {
                key = this.sanitizeString(key);
                if (messageArgsInput[key] === "" || !!messageArgsInput[key]) {
                    messageArgs[key] = this.sanitizeString(messageArgsInput[key]);
                }
            });
            messageArgsInput = messageArgs;
        }
        return messageArgsInput;
    }

    sanitizeNotif(notif: NotificationResponse): NotificationResponse {
        notif.messageCode = this.sanitizeString(notif.messageCode);
        notif.messageArgs = this.sanitizeObj(notif.messageArgs);
        return notif;
    }


    connectToProgressBarWorkflow(messageCallbackType: (message: IMessage) => void) {
        this.connect(this.NOTIFICATION_TOPIC, messageCallbackType);
    }

    connectToRecortNotification(messageCallbackType: (message: IMessage) => void) {
        this.connect(this.NOTIFICATION_RECORD_STATUS_TOPIC, messageCallbackType);
    }

    unsubscribeMessage() {
        this.subscription.unsubscribe();
    }

    private connect(destinationTopic: string, messageCallbackType: (message: IMessage) => void) {
        this.messages = this.stompService.watch(destinationTopic);
        this.subscription = this.messages.subscribe(response => messageCallbackType(response));
    }

    disconnect(): void {
        this.stompService.deactivate();
    }
}
