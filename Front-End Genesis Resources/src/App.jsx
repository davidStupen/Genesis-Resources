import {Route, Routes} from "react-router-dom"
import Home from "./pages/Home"
import Navbar from "./componens/Navbar"

function App() {
  return(
    <div>
      <div>
        <Navbar />
      </div>
      <Routes>
        <Route path="/" element={<Home/>}/>
      </Routes>
    </div>
  )
}

export default App
