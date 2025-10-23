import {Route, Routes} from "react-router-dom"
import Home from "./pages/Home"
import Navbar from "./componens/Navbar"
import Update from "./pages/Update"

function App() {
  return(
    <div>
      <div>
        <Navbar />
      </div>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/update/:currentId" element={<Update/>}/>
      </Routes>
    </div>
  )
}

export default App
