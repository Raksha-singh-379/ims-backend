import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface IWareHouse {
  id?: number;
  whName?: string | null;
  address?: string | null;
  pincode?: number | null;
  city?: string | null;
  state?: string | null;
  country?: string | null;
  gSTDetails?: string | null;
  managerName?: string | null;
  managerEmail?: string | null;
  managerContact?: string | null;
  contact?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  wareHouseId?: number | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  securityUsers?: ISecurityUser[] | null;
}

export class WareHouse implements IWareHouse {
  constructor(
    public id?: number,
    public whName?: string | null,
    public address?: string | null,
    public pincode?: number | null,
    public city?: string | null,
    public state?: string | null,
    public country?: string | null,
    public gSTDetails?: string | null,
    public managerName?: string | null,
    public managerEmail?: string | null,
    public managerContact?: string | null,
    public contact?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public wareHouseId?: number | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public securityUsers?: ISecurityUser[] | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getWareHouseIdentifier(wareHouse: IWareHouse): number | undefined {
  return wareHouse.id;
}
