import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';

export interface ITransferRecieved {
  id?: number;
  transferDate?: dayjs.Dayjs | null;
  qtyTransfered?: number | null;
  qtyReceived?: number | null;
  comment?: string | null;
  freeField1?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  securityUser?: ISecurityUser | null;
  transfer?: ITransfer | null;
}

export class TransferRecieved implements ITransferRecieved {
  constructor(
    public id?: number,
    public transferDate?: dayjs.Dayjs | null,
    public qtyTransfered?: number | null,
    public qtyReceived?: number | null,
    public comment?: string | null,
    public freeField1?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null,
    public securityUser?: ISecurityUser | null,
    public transfer?: ITransfer | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getTransferRecievedIdentifier(transferRecieved: ITransferRecieved): number | undefined {
  return transferRecieved.id;
}
