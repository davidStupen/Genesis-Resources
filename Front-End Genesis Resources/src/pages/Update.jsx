import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"

const Update = () => {
  const navigate = useNavigate()
  const {currentId} = useParams()
  const [fullName, setFullName] = useState({id:-1, name:"", surname:""})
  const handlerFullName = e => {
    const {name, value} = e.target
    setFullName(item => ({
      ...item,
      [name]: value
    }))
  }
  const handlerFullNameSubmit = async (e) => {
    e.preventDefault() 
    try{
      const response = await axios.put("http://13.48.48.105:8080/api/v1/users", fullName)
      navigate("/")
    } catch(err){
      console.error(err)
    }
  }
  useEffect(() => {
    console.log(currentId)
    try {
      const fetch = async () => {
        const response = await axios.get(`http://13.48.48.105:8080/api/v1/users/${currentId}`)
        setFullName({id:currentId, name:response.data.name, surname:response.data.surname})
      }
      fetch()
    } catch (e) {
      setErr(e)
    }
  }, [])
  return(
    <div>
      <h1>Update page</h1>
      <div>
        <form onSubmit={handlerFullNameSubmit}>
          <input type="text" onChange={handlerFullName} value={fullName.name} name="name"/>
          <input type="text" onChange={handlerFullName} value={fullName.surname} name="surname"/>
          <input type="submit" value="Upravit"/>
        </form>
      </div>
    </div>
  )
}
export default Update
