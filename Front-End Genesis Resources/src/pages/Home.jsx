import { useEffect, useState } from "react"
import axios from "axios"
import DeleteBtn from "../componens/DeleteBtn"
import { Link } from "react-router-dom"
import Search from "../componens/Search"
const Home = () => {
  const [data, setData] = useState([])
  const [err, setErr] = useState("")
  const [con, setCon] = useState(false)
  const handlerSearch = searchData => {
    setData(searchData)
  }
  useEffect(() => {
      const fetch = async () => {
        try{
          const response = await axios.get("http://localhost:8080/api/v1/users", data)
          setData(response.data)
          setErr("")
        } catch(e){
          setErr("chyba serveru: " + e)
        }
      }
      fetch()
  }, [con])
  const control = () => {
    if(con){
      setCon(false)
    } else{
      setCon(true)
    }
  }
  return(
    <div>
      <h1>Genesis Resources</h1>
      {
        data.length > 0 && (
          <div>
            <Search search={handlerSearch}/>
            {
              data.map(item => <div key={item.id}>  
                                  <h3>Jmeno: {item.name}, Přijmení: {item.surname}</h3>
                                  <DeleteBtn clickId={item.id} onclick={control}/>
                                  <Link to={`/update/${item.id}`}><li>Upravit</li></Link>
                              </div>)
            }
          </div>
        )
      }
      <p>{err}</p>
    </div>
  )
}
export default Home
