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
  gstinNumber?: string | null;
  description?: string | null;
  clientType?: ClientType | null;
  isactivated?: boolean | null;
  freeField1?: string | null;
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
    public gstinNumber?: string | null,
    public description?: string | null,
    public clientType?: ClientType | null,
    public isactivated?: boolean | null,
    public freeField1?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null
  ) {
    this.isactivated = this.isactivated ?? false;
  }
}

export function getClientDetailsIdentifier(clientDetails: IClientDetails): number | undefined {
  return clientDetails.id;
}
