export type NotificationCategory = string;

export const NotificationCategoryToast: NotificationCategory = 'TOAST';
export const NotificationCategoryProgressbar: NotificationCategory = 'PROGRESS_BAR';

export enum NotificationEventType {
    success,
    error,
    info,
    warning
}

export enum EventTypeEnum {
    EXPORT,
    WORKFLOW_PROGRESS,
}

export interface NotificationModel {
    type: NotificationEventType,
    tenantId: string,
    messageArgs?: {[key: string]: string},
    userId: string,
    sourceModule: string,
    operatorPublicId: string,
    errorCode?: string,
    messageCode?: string,
    eventType: EventTypeEnum
    category: NotificationCategory
}

export interface ToastNotificationModel extends NotificationModel{
    status?: string
}