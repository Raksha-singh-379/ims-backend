import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IWareHouse } from 'app/entities/ware-house/ware-house.model';
import { TransactionType } from 'app/entities/enumerations/transaction-type.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IProductTransaction {
  id?: number;
  refrenceId?: number | null;
  transactionType?: TransactionType | null;
  transactionStatus?: Status | null;
  transactionDate?: string | null;
  description?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  securityUser?: ISecurityUser | null;
  wareHouse?: IWareHouse | null;
}

export class ProductTransaction implements IProductTransaction {
  constructor(
    public id?: number,
    public refrenceId?: number | null,
    public transactionType?: TransactionType | null,
    public transactionStatus?: Status | null,
    public transactionDate?: string | null,
    public description?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public securityUser?: ISecurityUser | null,
    public wareHouse?: IWareHouse | null
  ) {}
}

export function getProductTransactionIdentifier(productTransaction: IProductTransaction): number | undefined {
  return productTransaction.id;
}
