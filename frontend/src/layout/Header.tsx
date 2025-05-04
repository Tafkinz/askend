import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';

const pages = ['Filters'];

const ResponsiveAppBar = () => {

  return (
    <AppBar position="static">
      <Container>
        <Toolbar disableGutters>
          <Box sx={{flexGrow: 1, display: {xs: 'flex'}}}>
            {pages.map((page) => (
              <Button
                key={page}
                href={"/"}
                sx={{my: 2, color: 'white', display: 'block'}}
              >
                {page}
              </Button>
            ))}
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export { ResponsiveAppBar as Header };
