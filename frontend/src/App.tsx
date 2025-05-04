import './App.css'
import { FilterModal } from './components/filter/FilterModal.tsx';
import { FilterTable } from './components/filter/FilterTable.tsx';
import { ErrorBoundary } from './layout/ErrorBoundary.tsx';
import { Header } from './layout/Header.tsx';
import { StoreContextProvider } from './store/StoreContextProvider.tsx';

const App = () => {

  return (
    <ErrorBoundary>
      <StoreContextProvider>
        <Header/>
        <FilterTable />
        <FilterModal isModal={false}/>
      </StoreContextProvider>
    </ErrorBoundary>
  )
}

export default App
