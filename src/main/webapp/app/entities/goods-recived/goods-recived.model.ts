import dayjs from 'dayjs/esm';
import { IPurchaseQuotation } from 'app/entities/purchase-quotation/purchase-quotation.model';

export interface IGoodsRecived {
  id?: number;
  grDate?: dayjs.Dayjs | null;
  qtyOrdered?: number | null;
  qtyRecieved?: number | null;
  manufacturingDate?: dayjs.Dayjs | null;
  expiryDate?: dayjs.Dayjs | null;
  lotNo?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  purchaseQuotation?: IPurchaseQuotation | null;
}

export class GoodsRecived implements IGoodsRecived {
  constructor(
    public id?: number,
    public grDate?: dayjs.Dayjs | null,
    public qtyOrdered?: number | null,
    public qtyRecieved?: number | null,
    public manufacturingDate?: dayjs.Dayjs | null,
    public expiryDate?: dayjs.Dayjs | null,
    public lotNo?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public purchaseQuotation?: IPurchaseQuotation | null
  ) {}
}

export function getGoodsRecivedIdentifier(goodsRecived: IGoodsRecived): number | undefined {
  return goodsRecived.id;
}
