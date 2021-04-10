import './App.css';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button'
import Autocomplete from '@material-ui/lab/Autocomplete';
import { Component } from 'react';
class App extends Component {

  constructor() {
    super();
    this.state = {
        cities: [],
        showButton: false,
    };
  }

  componentDidMount() {
    const citiesUrl = 'http://localhost:8080/api/cities';
    
    fetch(citiesUrl)
      .then((response) => response.json())
      .then((data) => {
        console.log('This is your data', data)
        this.setState({
          cities: data,
        });
      });
  }

  render() {
    return <div className="App">
      <header className="Search-area">
        <p>welcome to uni-helper!</p>
        <p>search for a town:</p>
      
        <Autocomplete
          id="combo-box-demo"
          options={this.state.cities}
          getOptionLabel={(option) => option.cityName}
          style={{ width: 300 }}
          renderInput={(params) => <TextField {...params} label="Town" variant="outlined" />}
          // value={value}
          // onChange={(event, newValue) => {
          //   setValue(newValue);
          // }}
          // inputValue={inputValue}
          // onInputChange={(event, newInputValue) => {
          //   setInputValue(newInputValue);
          // }}
        />

        {this.state.showButton && <Button>Search</Button>}
      </header>
    </div>
    }
}

export default App;
