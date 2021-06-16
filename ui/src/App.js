import './App.css';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button'
import Autocomplete from '@material-ui/lab/Autocomplete';
import { Component } from 'react';
import searchImg from './search.png'
import uniImg from './mortarboard.png'
class App extends Component {

  
  constructor() {
    super();
    this.state = {
        cities: [],
        showButton: false,
        value: null,
        inputValue: null,
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
        <div>
          <img src={searchImg} className="Logo" alt="magnifying glass" />
          <img src={uniImg} className="Logo" alt="mortarboard" />
        </div>

        <p>welcome to uni-helper!</p>
        <p>search for a town:</p>
      
        <Autocomplete
          id="combo-box-demo"
          options={this.state.cities}
          getOptionLabel={(option) => option.cityName}
          style={{ width: 300 }}
          renderInput={(params) => <TextField {...params} label="Town" variant="outlined" />}
           onChange={(event, newValue) => {
             this.setState({value: newValue});
             this.setState({showButton: true}); 
           }}
           onInputChange={(event, newInputValue) => {
             this.setState({inputValue: newInputValue});
           }}
        />

        {this.state.showButton && <Button className="btn" size="large" color="primary">Search</Button>}

        <div>{`value: ${this.state.value !== null ? `${this.state.value}` : 'null'}`}</div>
        <div>{`inputValue: ${this.state.inputValue}`}</div>
      </header>
    </div>
    }
}

export default App;
