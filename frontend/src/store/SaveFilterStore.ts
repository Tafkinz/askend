import { useState } from 'react';
import { Api } from '../api/ApiUtil.ts';
import { FetchStatus, IFilter } from '../typings/Filter';
import { IStoreContextProps } from './StoreContext.ts';

export interface ISaveFilterStore {
  saveFilter: (criteria: IFilter) => Promise<void>;
  postFilterStatus: FetchStatus;
}
const SaveFilterStore = (stores: Partial<IStoreContextProps>): ISaveFilterStore => {
  const [postFilterStatus, setPostFilterStatus] = useState<FetchStatus>('UNINITIALIZED');
  let postFilterTimeout: number | null = null;

  const saveFilter = async (filter: IFilter) => {
    if (postFilterTimeout) {
      clearTimeout(postFilterTimeout);
    }
    try {
      setPostFilterStatus('FETCHING');
      const response = await Api.post("/filter", filter);
      stores.viewFilterStore!.addFilter(response.data);
      setPostFilterStatus('FETCHED');
    } catch (e) {
      console.error(e);
      setPostFilterStatus('ERROR');
    }
    finally {
      postFilterTimeout = setTimeout(() => {
        setPostFilterStatus('UNINITIALIZED')
      }, 5000);
    }
    return Promise.resolve();
  }
  return { saveFilter, postFilterStatus };
}

export { SaveFilterStore };
