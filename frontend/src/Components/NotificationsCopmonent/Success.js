import React from 'react'
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';

export const Success = () => {
    return (
        <Stack sx={{ width: '100%' }} spacing={2}>
      <Alert severity="success">Der Vorgang wurde erfolgreich durgeführt!</Alert>
    </Stack>
  );
}

export default Success;
