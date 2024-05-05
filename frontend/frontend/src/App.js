import './index.css';
import MyRouter from './router/index';
import Header from './components/Header';

function App() {
  return (
    <div className="App">
      <Header />
      <MyRouter />
    </div>
  );
}

export default App;
