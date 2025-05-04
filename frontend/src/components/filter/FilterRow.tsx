import { RemoveCircleOutline } from '@mui/icons-material';
import { Grid, MenuItem, TextField } from '@mui/material';
import Button from '@mui/material/Button';
import { useStores } from '../../store/StoreContext.ts';
import { ICriteria } from '../../typings/Filter';
import { capitalizeFirstLetter } from '../../util/TextUtil.ts';

interface IFilterRowProps {
  deletable: boolean;
  criteria: ICriteria;
  updateValue: (criteria: ICriteria) => void;
  onDelete: () => void;
}

const FilterRow = (props: IFilterRowProps) => {
  const {criteria, deletable, updateValue, onDelete} = props;
  const {criteriaStore} = useStores();
  const updateCriteriaType = (type: string) => {
    const newCriteria = criteriaStore!.criteriaTypes.find((ct) => ct.name === type);
    updateValue({criteria: newCriteria!.name, value: newCriteria!.comparators![0].defaultValue, comparator: newCriteria!.comparators[0].comparator})
  }
  return (
    <>
      <Grid justifyContent={'space-between'} container={true} spacing={1}>
        <Grid alignContent={'center'} size={{xs: 12, sm: 6, md: 4}}>
          <TextField
            value={criteria.criteria}
            onChange={(event) => updateCriteriaType(event.target.value)}
            sx={{width: '90%'}}
            id={'type-select'}
            select={true}
            error={!criteria.criteria}
            helperText={!criteria.criteria && 'Field is mandatory'}
            variant={'standard'}
            label={'Select type'}>
            {criteriaStore!.criteriaTypes!.map((ct) =>
              <MenuItem key={ct.name} value={ct.name}>{capitalizeFirstLetter(ct.name)}</MenuItem>
            )}
          </TextField>
        </Grid>
        <Grid alignContent={'center'} size={{xs: 12, sm: 6, md: 4}}>
          <TextField value={criteria.comparator}
                     onChange={(event) => updateValue({...criteria, comparator: event.target.value})}
                     sx={{width: '90%'}}
                     id={'type-select'}
                     error={!criteria.comparator}
                     helperText={!criteria.comparator && 'Field is mandatory'}
                     select={true}
                     variant={'standard'}
                     label={'Select criteria'}>
            {criteriaStore?.criteriaTypes!.find((c) => c.name === criteria.criteria)?.comparators.map((co) =>
              <MenuItem key={co.comparator} value={co.comparator}>{capitalizeFirstLetter(co.comparator)}</MenuItem>
            )}
          </TextField>
        </Grid>
        <Grid size={{xs: 12, sm: 3}} alignContent={'center'}>
          <TextField type={criteriaStore!.criteriaTypes.find((c) => c.name === criteria.criteria)!.type}
                     value={criteria.value}
                     aria-valuemax={Number.MAX_SAFE_INTEGER}
                     aria-valuemin={Number.MIN_SAFE_INTEGER}
                     onChange={(event) => updateValue({...criteria, value: event.target.value})}
                     sx={{width: '90%'}}
                     size={'medium'}
                     error={criteria.value !== 0 && !criteria.value}
                     helperText={criteria.value !== 0 && !criteria.value && 'Field is mandatory'}
                     id={'type-select'}
                     variant={'standard'}
                     label={'Select value'}/>
        </Grid>
        <Grid alignContent={'center'} justifyContent={'center'} size={'grow'}>
          <Button color={'warning'}
                  onClick={onDelete}
                  disabled={!deletable}
                  size={'small'}>Delete&nbsp;
            <RemoveCircleOutline/></Button>
        </Grid>
      </Grid>
      <br/>
    </>
  )
}

export { FilterRow };
