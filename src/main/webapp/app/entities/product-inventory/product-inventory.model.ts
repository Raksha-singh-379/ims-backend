import dayjs from 'dayjs/esm';
import { IConsumptionDetails } from 'app/entities/consumption-details/consumption-details.model';
import { IProduct } from 'app/entities/product/product.model';
import { IProductTransaction } from 'app/entities/product-transaction/product-transaction.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IWareHouse } from 'app/entities/ware-house/ware-house.model';

export interface IProductInventory {
  id?: number;
  inwardOutwardDate?: dayjs.Dayjs | null;
  inwardQty?: string | null;
  outwardQty?: string | null;
  totalQuanity?: string | null;
  pricePerUnit?: number | null;
  lotNo?: string | null;
  expiryDate?: dayjs.Dayjs | null;
  inventoryTypeId?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  consumptionDetails?: IConsumptionDetails[] | null;
  product?: IProduct | null;
  productTransaction?: IProductTransaction | null;
  securityUser?: ISecurityUser | null;
  wareHouse?: IWareHouse | null;
}

export class ProductInventory implements IProductInventory {
  constructor(
    public id?: number,
    public inwardOutwardDate?: dayjs.Dayjs | null,
    public inwardQty?: string | null,
    public outwardQty?: string | null,
    public totalQuanity?: string | null,
    public pricePerUnit?: number | null,
    public lotNo?: string | null,
    public expiryDate?: dayjs.Dayjs | null,
    public inventoryTypeId?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public consumptionDetails?: IConsumptionDetails[] | null,
    public product?: IProduct | null,
    public productTransaction?: IProductTransaction | null,
    public securityUser?: ISecurityUser | null,
    public wareHouse?: IWareHouse | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getProductInventoryIdentifier(productInventory: IProductInventory): number | undefined {
  return productInventory.id;
}
