import { useEffect, useState } from "react"
import axios from "axios"
const Home = () => {
  const [data, setData] = useState([])
  const [err, setErr] = useState("")
  useEffect(() => {
    try{
      const fetch = async () => {
        const response = await axios.get("http://localhost:8080", data)
        setData(response.data)
      }
      fetch()
    } catch(e){
      setErr(e)
    }
  
  }, [])
  return(
    <div>
      <h1>Genesis Resources</h1>
      {
        data.length > 0 && (
          <div>
            {
              data.map(item => <div key={item.id}>  
                                  <h3>Jmeno: {item.name} Přijmení: {item.surname}</h3>
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
