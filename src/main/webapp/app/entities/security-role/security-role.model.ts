import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';

export interface ISecurityRole {
  id?: number;
  name?: string;
  description?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  securityPermissions?: ISecurityPermission[] | null;
  securityUsers?: ISecurityUser[] | null;
}

export class SecurityRole implements ISecurityRole {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public securityPermissions?: ISecurityPermission[] | null,
    public securityUsers?: ISecurityUser[] | null
  ) {}
}

export function getSecurityRoleIdentifier(securityRole: ISecurityRole): number | undefined {
  return securityRole.id;
}
