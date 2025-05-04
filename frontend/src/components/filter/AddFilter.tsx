import { AddCircleOutline } from '@mui/icons-material';
import { Alert, Button, DialogActions, DialogTitle, Grid, TextField } from '@mui/material';
import * as React from 'react';
import { useStores } from '../../store/StoreContext.ts';
import { ICriteria, ICriteriaType } from '../../typings/Filter';
import { FilterRow } from './FilterRow.tsx';

interface IAddFilterProps {
  closeModal: () => void;
}

const AddFilter = (props: IAddFilterProps) => {
  const {criteriaStore, saveFilterStore} = useStores();

  const defaultCriteria = criteriaStore!.criteriaTypes.find((ct) => ct.default);
  const createDefaultCriteria = (defaultCriteria: ICriteriaType) => {
    return {
      criteria: defaultCriteria!.name,
      value: defaultCriteria!.comparators[0].defaultValue,
      comparator: defaultCriteria!.comparators[0].comparator
    }
  };
  const [criteria, setCriteria] = React.useState<ICriteria[]>([createDefaultCriteria(defaultCriteria!)])
  const [title, setTitle] = React.useState('');

  const filterSaveNotice = () => {
    if (saveFilterStore?.postFilterStatus === 'ERROR') {
      return <Alert severity={'error'}>Error during filter save</Alert>
    }
    if (saveFilterStore?.postFilterStatus === 'FETCHED') {
      return <Alert severity={'success'}>Filter created successfully&nbsp;<Button onClick={props.closeModal}>Close</Button></Alert>
    }
    return null;
  }

  const isCriteriaValid = (c: ICriteria) => {
    return (c.value === 0 || !!c.value) && !!c.criteria && !!c.comparator;
  }

  const isSaveEnabled = () => {
    return criteria.every(isCriteriaValid) && !!title;
  }

  return (
    <>
      <DialogTitle id="filter-modal-title">Add a new filter</DialogTitle>
      <Grid container={true} rowSpacing={2} alignContent={'center'} alignItems={'center'} justifyContent={'center'}>
        <Grid size={{xs: 12, sm: 4}} justifyContent={'center'} alignContent={'center'}>
          <TextField fullWidth={true}
                     id={'filter-name-textfield'}
                     label={'Filter name'}
                     value={title}
                     error={!title}
                     helperText={!title && 'Field is mandatory'}
                     onChange={(e) => setTitle(e.target.value)}
                     type={'text'}
                     variant={'standard'}/>
        </Grid>
        <Grid size={12}>
          {criteria.slice().map((c, idx) =>
            (
              <FilterRow key={c.id}
                         deletable={criteria.length > 1}
                         criteria={c}
                         updateValue={(criteria) => {
                           setCriteria((prev) => {
                             const newState = [...prev];
                             if (idx >= 0) {
                               newState[idx] = criteria;
                             }
                             return newState;
                           })
                         }}
                         onDelete={() => setCriteria((prev) => {
                           const newState = [...prev];
                           newState.splice(idx, 1);
                           return newState;
                         })}
              />
            ))}
        </Grid>
        <Grid size={2} justifyContent={'center'} alignContent={'center'}>
          <Button color={'success'} size={'small'} onClick={() => setCriteria((prev) => [...prev, createDefaultCriteria(defaultCriteria!)])}>Add row&nbsp;
            <AddCircleOutline/></Button>
        </Grid>
      </Grid>
      <br/>
      {filterSaveNotice()}
      <br/>
      <DialogActions>
        <Button onClick={props.closeModal}>Cancel</Button>
        <Button
          disabled={!isSaveEnabled() || saveFilterStore?.postFilterStatus === 'FETCHING'}
          loading={saveFilterStore?.postFilterStatus === 'FETCHING'}
          onClick={() => {
            saveFilterStore?.saveFilter({criteria, name: title})
          }}
        >Save</Button>
      </DialogActions>
    </>
  )
}

export { AddFilter };
