export interface ICriteriaType {
  id?: number;
  type: string;
  name: string;
  default: boolean;
  comparators: IComparator[];
}

export interface IComparator {
  comparator: string;
  defaultValue: number | string | Date;
}

export interface ICriteria {
  id?: number;
  criteria: string;
  comparator: string;
  value: number | string | Date;
}

export interface IFilter {
  criteria: ICriteria[];
  name: string;
}

export interface IPaged<T> {
  total: number;
  currentPage: number;
  items: T[];
}

export type FetchStatus = 'UNINITIALIZED' | 'FETCHING' | 'FETCHED' | 'ERROR';
