import dayjs from 'dayjs/esm';

export interface IProject {
  id?: number;
  projectName?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  departmentName?: string | null;
  budget?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  lastModified?: string | null;
  lastModifiedBy?: string | null;
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public projectName?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public departmentName?: string | null,
    public budget?: string | null,
    public freeField1?: string | null,
    public freeField2?: string | null,
    public freeField3?: string | null,
    public lastModified?: string | null,
    public lastModifiedBy?: string | null
  ) {}
}

export function getProjectIdentifier(project: IProject): number | undefined {
  return project.id;
}
