export interface ICategories {
  id?: number;
  categoryName?: string | null;
  categoryDescription?: string | null;
  freeField1?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
  isDeleted?: boolean | null;
  isActive?: boolean | null;
}

export class Categories implements ICategories {
  constructor(
    public id?: number,
    public categoryName?: string | null,
    public categoryDescription?: string | null,
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

export function getCategoriesIdentifier(categories: ICategories): number | undefined {
  return categories.id;
}
