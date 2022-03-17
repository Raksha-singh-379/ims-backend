import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IProject } from 'app/entities/project/project.model';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';

export interface IConsumptionDetails {
  id?: number;
  comsumptionDate?: dayjs.Dayjs | null;
  qtyConsumed?: number | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  securityUser?: ISecurityUser | null;
  project?: IProject | null;
  productInventory?: IProductInventory | null;
}

export class ConsumptionDetails implements IConsumptionDetails {
  constructor(
    public id?: number,
    public comsumptionDate?: dayjs.Dayjs | null,
    public qtyConsumed?: number | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public securityUser?: ISecurityUser | null,
    public project?: IProject | null,
    public productInventory?: IProductInventory | null
  ) {}
}

export function getConsumptionDetailsIdentifier(consumptionDetails: IConsumptionDetails): number | undefined {
  return consumptionDetails.id;
}
