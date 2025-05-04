import * as React from 'react';
import { CriteriaStore } from './CriteriaStore.ts';
import { SaveFilterStore } from './SaveFilterStore.ts';
import { StoreContext } from './StoreContext.ts';
import { ViewFilterStore } from './ViewFilterStore.ts';

const StoreContextProvider = (props: { children: React.ReactNode }) => {
  const criteriaStore = CriteriaStore();
  const viewFilterStore = ViewFilterStore();
  const saveFilterStore = SaveFilterStore({viewFilterStore});
  return (
    <StoreContext.Provider value={{
      criteriaStore,
      viewFilterStore,
      saveFilterStore
    }}>
      {props.children}
    </StoreContext.Provider>
  )
}

export { StoreContextProvider };
