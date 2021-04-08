import logo from './logo.svg';
import './App.css';
import { Button } from '@material-ui/core';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
function App() {

  // Will get these from API
  const top100Films = [
    { title: 'London', year: 1994 },
    { title: 'Birmingham', year: 1972 },
    { title: 'Bristol', year: 1974 },
  ];

  return (
    <div className="App">
      <header className="Search-area">
        <p>welcome to uni-helper!</p>
        <p>search for a town:</p>
      
        <Autocomplete
          id="combo-box-demo"
          options={top100Films}
          getOptionLabel={(option) => option.title}
          style={{ width: 300 }}
          renderInput={(params) => <TextField {...params} label="Town" variant="outlined" />}
        />
      </header>
    </div>
  );
}

export default App;
