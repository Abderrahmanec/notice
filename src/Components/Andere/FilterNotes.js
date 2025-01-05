import React from 'react';
import { Box, TextField, Select, MenuItem, Button } from '@mui/material';

function FilterNotes() {
  return (
    <Box display="flex" alignItems="center" gap={2}>
      <TextField label="Tag" />
      <Select label="Type" defaultValue="">
        <MenuItem value="image">Image</MenuItem>
        <MenuItem value="link">Link</MenuItem>
        <MenuItem value="text">Text</MenuItem>
      </Select>
      <TextField label="Date Range" type="date" InputLabelProps={{ shrink: true }} />
      <Button variant="contained" color="primary">
        Filter
      </Button>
    </Box>
  );
}

export default FilterNotes;
