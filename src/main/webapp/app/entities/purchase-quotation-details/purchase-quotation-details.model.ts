import { IProduct } from 'app/entities/product/product.model';
import { IPurchaseQuotation } from 'app/entities/purchase-quotation/purchase-quotation.model';

export interface IPurchaseQuotationDetails {
  id?: number;
  qtyordered?: number | null;
  gstTaxPercentage?: number | null;
  pricePerUnit?: number | null;
  totalPrice?: number | null;
  discount?: number | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  products?: IProduct[] | null;
  purchaseQuotation?: IPurchaseQuotation | null;
}

export class PurchaseQuotationDetails implements IPurchaseQuotationDetails {
  constructor(
    public id?: number,
    public qtyordered?: number | null,
    public gstTaxPercentage?: number | null,
    public pricePerUnit?: number | null,
    public totalPrice?: number | null,
    public discount?: number | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public products?: IProduct[] | null,
    public purchaseQuotation?: IPurchaseQuotation | null
  ) {}
}

export function getPurchaseQuotationDetailsIdentifier(purchaseQuotationDetails: IPurchaseQuotationDetails): number | undefined {
  return purchaseQuotationDetails.id;
}
