import axios from "axios"
import { useEffect, useState } from "react"

const Search = (props) => {
  const [search, setSearch] = useState("")
  const [allData, setAllData] = useState([])
  const handlerSearch = async e => {
    setSearch(e.target.value)
      if(search.length > 2){
        const response = await axios.get(`http://13.60.230.45:8080/api/v1/users/search?name=${search}`)
        props.search(response.data)
      } else{
        props.search(allData)
      }
  }
  useEffect(() => {
    const fetch = async () => {
      const response = await axios.get("http://13.60.230.45:8080/api/v1/users")
        setAllData(response.data)
    }
    fetch()
  }, [])
  return(
    <div>
      <input type="text" placeholder="hledání" onChange={handlerSearch}/>
    </div>
  )
}
export default Search
