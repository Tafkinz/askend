import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Alert,
  Pagination,
  Paper,
  Skeleton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography
} from '@mui/material';
import { useEffect } from 'react';
import { PER_PAGE } from '../../store/CriteriaStore.ts';
import { useStores } from '../../store/StoreContext.ts';
import { ICriteria } from '../../typings/Filter';

const FilterTable = () => {
  const {viewFilterStore} = useStores();

  useEffect(() => {
    console.log('FilterTable useEffect');
    viewFilterStore?.fetchFilters();
  }, []);

  if (['UNINITIALIZED', 'FETCHING'].includes(viewFilterStore!.filterStatus)) {
    return <Skeleton width={'400px'} height={'400px'}/>
  }

  if (viewFilterStore!.filterStatus === 'ERROR') {
    return <Alert severity="error">Technical error while loading filters</Alert>;
  }

  const calculateValue = (value: ICriteria['value']): string => {
    if (typeof value === 'number') {
      return `${value}`;
    }
    if (typeof value === 'string') {
      return value;
    }
    return value.toDateString();
  }

  return (
    <>
      {viewFilterStore!.filter?.items.map((filterItem, idx) => {
        return (
          <Accordion sx={{background: '#b3dae0'}} key={filterItem.name + idx}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon/>}
              aria-controls={`filter-${idx}-content`}
              id={`panel${idx}-header`}
            >
              <Typography component="span">{filterItem.name}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <TableContainer component={Paper} sx={{background: '#aac7ce'}}>
                <Table sx={{minWidth: 650}} size={'small'} aria-label={`filter table ${filterItem.name}`}>
                  <TableHead>
                    <TableRow>
                      <TableCell sx={{fontWeight: 'bold'}} align="left">Criteria</TableCell>
                      <TableCell sx={{fontWeight: 'bold'}} align="left">Comparator</TableCell>
                      <TableCell sx={{fontWeight: 'bold'}} align="left">Value</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {filterItem.criteria.map((row) => (
                      <TableRow
                        key={row.id}
                        sx={{'&:last-child td, &:last-child th': {border: 0}}}
                      >
                        <TableCell align={'left'} component="th" scope="row">
                          {row.criteria}
                        </TableCell>
                        <TableCell align="left">{row.comparator}</TableCell>
                        <TableCell align="left">{calculateValue(row.value)}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </AccordionDetails>
          </Accordion>
        )
      })}
      <br/>
      <Pagination page={viewFilterStore?.filterPage}
                  onChange={(_ev, val) => viewFilterStore?.changePage(val)}
                  count={Math.ceil((viewFilterStore!.filter?.total || 1) / PER_PAGE)}
                  variant="outlined"
                  color="primary"/>
    </>
  )
}

export { FilterTable };
