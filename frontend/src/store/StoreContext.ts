import * as React from 'react';
import { ICriteriaStore } from './CriteriaStore.ts';
import { ISaveFilterStore } from './SaveFilterStore.ts';
import { IViewFilterStore } from './ViewFilterStore.ts';

export interface IStoreContextProps {
  criteriaStore: ICriteriaStore | null;
  viewFilterStore: IViewFilterStore | null;
  saveFilterStore: ISaveFilterStore | null;
}

const StoreContext = React.createContext<IStoreContextProps>({
  criteriaStore: null,
  viewFilterStore: null,
  saveFilterStore: null
});

const useStores = () => {
  return React.useContext(StoreContext);
}

export { useStores, StoreContext };
