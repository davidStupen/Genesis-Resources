import axios from "axios"
import { useState } from "react"
import { useNavigate } from "react-router-dom"

const AddUser = () => {
  const navigate = useNavigate()
  const [data, setData] = useState({name:"", surname:"", personId:"", })
  const [err, setErr] = useState("")
  const handlerData = e => {
    const {name, value} = e.target
    setData(item => ({
      ...item,
      [name]: value
    }))
  }
  const handlerDataSubmit = async (e) => {
    e.preventDefault()
    try{
      const response = await axios.post("http://13.48.48.105:8080/api/v1/users", data)
      setData(response.data)
      setErr("")
      navigate("/")
    } catch(e){
      if(e.response?.status === 400){
        setErr(e.response.data)
        navigate("/add")
      }else{
        setErr("něco se pokazilo")
        navigate("/add")
      }
    }
  }
   return(
    <div>
      <div>
        <form onSubmit={handlerDataSubmit}>
          <input type="text" onChange={handlerData} name="name" value={data.name} placeholder="Jméno"/>
          <input type="text" onChange={handlerData} name="surname" value={data.surname} placeholder="Přijmení"/>
           <input type="text" onChange={handlerData} name="personId" value={data.personId} placeholder="vaše ID"/>
           <input type="submit" value="Uložit"/>
        </form>
      </div>
      {
        err.length > 0 && <p>Error: {err}</p>
      }
    </div>
  )
} 
export default AddUser
