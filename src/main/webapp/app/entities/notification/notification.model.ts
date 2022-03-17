import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IWareHouse } from 'app/entities/ware-house/ware-house.model';
import { NotificationType } from 'app/entities/enumerations/notification-type.model';

export interface INotification {
  id?: number;
  massage?: string;
  notificationType?: NotificationType | null;
  title?: string | null;
  isActionRequired?: boolean | null;
  isRead?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  securityUser?: ISecurityUser | null;
  wareHouse?: IWareHouse | null;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public massage?: string,
    public notificationType?: NotificationType | null,
    public title?: string | null,
    public isActionRequired?: boolean | null,
    public isRead?: boolean | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public securityUser?: ISecurityUser | null,
    public wareHouse?: IWareHouse | null
  ) {
    this.isActionRequired = this.isActionRequired ?? false;
    this.isRead = this.isRead ?? false;
  }
}

export function getNotificationIdentifier(notification: INotification): number | undefined {
  return notification.id;
}
