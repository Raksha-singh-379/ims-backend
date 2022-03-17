import dayjs from 'dayjs/esm';
import { ITransferDetails } from 'app/entities/transfer-details/transfer-details.model';
import { ITransferRecieved } from 'app/entities/transfer-recieved/transfer-recieved.model';
import { ITransferDetailsApprovals } from 'app/entities/transfer-details-approvals/transfer-details-approvals.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IWareHouse } from 'app/entities/ware-house/ware-house.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface ITransfer {
  id?: number;
  tranferDate?: dayjs.Dayjs | null;
  comment?: string | null;
  status?: Status | null;
  sourceWareHouse?: string | null;
  destinationWareHouse?: string | null;
  freeField1?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  transferDetails?: ITransferDetails[] | null;
  transferRecieveds?: ITransferRecieved[] | null;
  transferDetailsApprovals?: ITransferDetailsApprovals[] | null;
  securityUser?: ISecurityUser | null;
  wareHouse?: IWareHouse | null;
}

export class Transfer implements ITransfer {
  constructor(
    public id?: number,
    public tranferDate?: dayjs.Dayjs | null,
    public comment?: string | null,
    public status?: Status | null,
    public sourceWareHouse?: string | null,
    public destinationWareHouse?: string | null,
    public freeField1?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public transferDetails?: ITransferDetails[] | null,
    public transferRecieveds?: ITransferRecieved[] | null,
    public transferDetailsApprovals?: ITransferDetailsApprovals[] | null,
    public securityUser?: ISecurityUser | null,
    public wareHouse?: IWareHouse | null
  ) {}
}

export function getTransferIdentifier(transfer: ITransfer): number | undefined {
  return transfer.id;
}
