import dayjs from 'dayjs/esm';
import { IPurchaseQuotationDetails } from 'app/entities/purchase-quotation-details/purchase-quotation-details.model';
import { IGoodsRecived } from 'app/entities/goods-recived/goods-recived.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { IProject } from 'app/entities/project/project.model';
import { IClientDetails } from 'app/entities/client-details/client-details.model';
import { OrderType } from 'app/entities/enumerations/order-type.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IPurchaseQuotation {
  id?: number;
  refrenceNumber?: string | null;
  totalPOAmount?: number | null;
  totalGSTAmount?: number | null;
  expectedDeliveryDate?: dayjs.Dayjs | null;
  poDate?: dayjs.Dayjs | null;
  orderType?: OrderType | null;
  orderStatus?: Status | null;
  termsAndCondition?: string | null;
  notes?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  purchaseQuotationDetails?: IPurchaseQuotationDetails[] | null;
  goodsReciveds?: IGoodsRecived[] | null;
  securityUser?: ISecurityUser | null;
  project?: IProject | null;
  clientDetails?: IClientDetails | null;
}

export class PurchaseQuotation implements IPurchaseQuotation {
  constructor(
    public id?: number,
    public refrenceNumber?: string | null,
    public totalPOAmount?: number | null,
    public totalGSTAmount?: number | null,
    public expectedDeliveryDate?: dayjs.Dayjs | null,
    public poDate?: dayjs.Dayjs | null,
    public orderType?: OrderType | null,
    public orderStatus?: Status | null,
    public termsAndCondition?: string | null,
    public notes?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public purchaseQuotationDetails?: IPurchaseQuotationDetails[] | null,
    public goodsReciveds?: IGoodsRecived[] | null,
    public securityUser?: ISecurityUser | null,
    public project?: IProject | null,
    public clientDetails?: IClientDetails | null
  ) {}
}

export function getPurchaseQuotationIdentifier(purchaseQuotation: IPurchaseQuotation): number | undefined {
  return purchaseQuotation.id;
}
