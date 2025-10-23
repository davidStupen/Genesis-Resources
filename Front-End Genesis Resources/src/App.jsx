import {Route, Routes} from "react-router-dom"
import Home from "./pages/Home"
import Navbar from "./componens/Navbar"
import Update from "./pages/Update"
import AddUser from "./pages/AddUser"

function App() {
  return(
    <div>
      <div>
        <Navbar />
      </div>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/add" element={<AddUser/>}/>
        <Route path="/update/:currentId" element={<Update/>}/>
      </Routes>
    </div>
  )
}

export default App
