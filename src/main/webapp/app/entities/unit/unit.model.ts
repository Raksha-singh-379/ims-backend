export interface IUnit {
  id?: number;
  unitName?: string | null;
  shortName?: string | null;
  freeField1?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
}

export class Unit implements IUnit {
  constructor(
    public id?: number,
    public unitName?: string | null,
    public shortName?: string | null,
    public freeField1?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null,
    public isDeleted?: boolean | null,
    public isActive?: boolean | null
  ) {
    this.isDeleted = this.isDeleted ?? false;
    this.isActive = this.isActive ?? false;
  }
}

export function getUnitIdentifier(unit: IUnit): number | undefined {
  return unit.id;
}
