import { ClientType } from 'app/entities/enumerations/client-type.model';

export interface IClientDetails {
  id?: number;
  clientName?: string;
  mobileNo?: string | null;
  email?: string | null;
  billingAddress?: string | null;
  companyName?: string | null;
  companyContactNo?: string | null;
  website?: string | null;
  gstinNumber?: string;
  description?: string | null;
  clientType?: ClientType | null;
  isactivated?: boolean | null;
  freeField1?: string | null;
  isApproved?: boolean | null;
  nameOfBeneficiary?: string | null;
  accountNumber?: string;
  bankName?: string | null;
  accountType?: string | null;
  ifscCode?: string | null;
  gstCertificateImageContentType?: string | null;
  gstCertificateImage?: string | null;
  panCardImageContentType?: string | null;
  panCardImage?: string | null;
  cancelledChequeImageContentType?: string | null;
  cancelledChequeImage?: string | null;
  udYogAadharImageContentType?: string | null;
  udYogAadharImage?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
}

export class ClientDetails implements IClientDetails {
  constructor(
    public id?: number,
    public clientName?: string,
    public mobileNo?: string | null,
    public email?: string | null,
    public billingAddress?: string | null,
    public companyName?: string | null,
    public companyContactNo?: string | null,
    public website?: string | null,
    public gstinNumber?: string,
    public description?: string | null,
    public clientType?: ClientType | null,
    public isactivated?: boolean | null,
    public freeField1?: string | null,
    public isApproved?: boolean | null,
    public nameOfBeneficiary?: string | null,
    public accountNumber?: string,
    public bankName?: string | null,
    public accountType?: string | null,
    public ifscCode?: string | null,
    public gstCertificateImageContentType?: string | null,
    public gstCertificateImage?: string | null,
    public panCardImageContentType?: string | null,
    public panCardImage?: string | null,
    public cancelledChequeImageContentType?: string | null,
    public cancelledChequeImage?: string | null,
    public udYogAadharImageContentType?: string | null,
    public udYogAadharImage?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null
  ) {
    this.isactivated = this.isactivated ?? false;
    this.isApproved = this.isApproved ?? false;
  }
}

export function getClientDetailsIdentifier(clientDetails: IClientDetails): number | undefined {
  return clientDetails.id;
}
