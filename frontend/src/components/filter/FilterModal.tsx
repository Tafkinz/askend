import { AddCircleOutline } from '@mui/icons-material';
import { Alert, Box, Modal } from '@mui/material';
import Button from '@mui/material/Button';
import * as React from 'react';
import { useEffect, useState } from 'react';
import { useStores } from '../../store/StoreContext.ts';
import { AddFilter } from './AddFilter';

interface IFilterModalProps {
  isModal: boolean;
}

const FilterModal = (props: IFilterModalProps): React.ReactNode => {
  const [isOpen, setIsOpen] = useState(false);
  const {criteriaStore} = useStores();

  useEffect(() => {
    criteriaStore?.fetchCriteriaTypes();
  }, []);

  if (['FETCHING', 'UNINITIALIZED'].includes(criteriaStore!.criteriaTypeStatus)) {
    return null;
  }

  if (criteriaStore!.criteriaTypeStatus === 'ERROR') {
    return <Alert severity="error">Technical error while loading data</Alert>;
  }

  const content = () => {
    const commonStyle = {
      bgcolor: 'aliceblue',
      border: '2px solid #000',
      boxShadow: 24,
      p: 2,
      width: '80%',
      minHeight: '200px',
      height: 'auto',
      resize: 'vertical',
      overflow: 'auto',
    }
    return <Box
      sx={{
        ...commonStyle,
        ...props.isModal &&
        {
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
        }
      }}
    >
      <AddFilter closeModal={() => setIsOpen(false)}/>
    </Box>;
  }

  return (
    <>
      <Button onClick={() => setIsOpen(prevState => !prevState)}>Add filter&nbsp;<AddCircleOutline/></Button>
      {props.isModal ?
        <Modal
          aria-modal={true}
          open={isOpen}
          onClose={() => setIsOpen(false)}
          aria-labelledby="filter-modal-title"
          aria-description="Modal for adding new filters"
        >
          {content()}
        </Modal> : (
          <div hidden={!isOpen}>{content()}</div>)}
    </>
  )
}

export { FilterModal };
