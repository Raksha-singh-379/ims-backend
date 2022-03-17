import dayjs from 'dayjs/esm';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { ITransfer } from 'app/entities/transfer/transfer.model';

export interface ITransferDetailsApprovals {
  id?: number;
  approvalDate?: dayjs.Dayjs | null;
  qtyRequested?: number | null;
  qtyApproved?: number | null;
  comment?: string | null;
  freeField1?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
  securityUser?: ISecurityUser | null;
  transfer?: ITransfer | null;
}

export class TransferDetailsApprovals implements ITransferDetailsApprovals {
  constructor(
    public id?: number,
    public approvalDate?: dayjs.Dayjs | null,
    public qtyRequested?: number | null,
    public qtyApproved?: number | null,
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

export function getTransferDetailsApprovalsIdentifier(transferDetailsApprovals: ITransferDetailsApprovals): number | undefined {
  return transferDetailsApprovals.id;
}
