import { useState } from 'react';
import { Api } from '../api/ApiUtil.ts';
import { FetchStatus, IFilter, IPaged } from '../typings/Filter';
import { PER_PAGE } from './CriteriaStore.ts';

export interface IViewFilterStore {
  filterPage: number;
  changePage: (page: number) => void;
  filterStatus: FetchStatus;
  filter: IPaged<IFilter> | undefined;
  fetchFilters: (fetchMore?: boolean) => Promise<void>;
  addFilter: (filter: IFilter) => void;
}

const ViewFilterStore = (): IViewFilterStore => {
  const [filterPage, setFilterPage] = useState(1);
  const [filterStatus, setFilterStatus] = useState<FetchStatus>('UNINITIALIZED');
  const [filter, setFilters] = useState<IPaged<IFilter>>();

  const changePage = (newPage: number) => {
    setFilterPage(newPage);
    fetchFilters(true, newPage);
  }

  const addFilter = (filter: IFilter) => {
    setFilters((prev) => ({total: prev!.total, currentPage: prev!.currentPage, items: [...prev!.items, filter]}))
  }

  const fetchFilters = async (fetchMore: boolean = false, page?: number) => {
    if (filterStatus === 'FETCHING') {
      return;
    }
    if (filterStatus === 'UNINITIALIZED' || fetchMore) {
      try {
        setFilterStatus('FETCHING');
        const response = await Api.get("/filter", {params: {perPage: PER_PAGE, page: page ?? filterPage}});
        setFilters({total: response.data.total, currentPage: response.data.currentPage, items: response.data.items});
        setFilterStatus('FETCHED')
      } catch (e) {
        console.error(e);
        setFilterStatus('ERROR');
      }
    }
  }
  return {changePage, fetchFilters, filter, filterStatus, filterPage, addFilter};
}
export { ViewFilterStore };
