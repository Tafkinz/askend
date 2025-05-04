import { useState } from 'react';
import { Api } from '../api/ApiUtil';
import { FetchStatus, ICriteriaType } from '../typings/Filter';

export interface ICriteriaStore {
  criteriaTypes: ICriteriaType[];
  fetchCriteriaTypes: () => Promise<void>;
  criteriaTypeStatus: FetchStatus;
}

export const PER_PAGE = 10;

const CriteriaStore = (): ICriteriaStore => {
  const [criteriaTypes, setCriteriaTypes] = useState<ICriteriaType[]>([]);
  const [criteriaTypeStatus, setCriteriaTypeStatus] = useState<FetchStatus>('UNINITIALIZED');

  const fetchCriteriaTypes = async () => {
    if (criteriaTypeStatus === 'FETCHING' || !!criteriaTypes.length) {
      return;
    }
    try {
      setCriteriaTypeStatus('FETCHING');
      const response = await Api.get('/criteria');
      setCriteriaTypes(response.data);
      setCriteriaTypeStatus('FETCHED');
    } catch (e) {
      console.error(e);
      setCriteriaTypeStatus('ERROR');
    }
  }

  return {criteriaTypes, fetchCriteriaTypes, criteriaTypeStatus};
}

export { CriteriaStore };
