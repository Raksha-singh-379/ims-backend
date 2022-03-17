import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';

export interface ITransferDetails {
  id?: number;
  approvalDate?: dayjs.Dayjs | null;
  qty?: number | null;
  comment?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  product?: IProduct | null;
  transfer?: ITransfer | null;
}

export class TransferDetails implements ITransferDetails {
  constructor(
    public id?: number,
    public approvalDate?: dayjs.Dayjs | null,
    public qty?: number | null,
    public comment?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public product?: IProduct | null,
    public transfer?: ITransfer | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getTransferDetailsIdentifier(transferDetails: ITransferDetails): number | undefined {
  return transferDetails.id;
}
