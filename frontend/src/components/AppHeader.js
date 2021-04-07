import styled from 'styled-components/macro'
import {Link} from "react-router-dom";
import logo from "../images/musicompass-logo.png"

export default function AppHeader() {
  return (
      <Header>
          <Link to={"/"}>
              <img src={logo}/>
          </Link>
      </Header>
  )
}

const Header = styled.header`
  background: var(--primary-color);
  box-shadow: 0px 2px 8px #333;
  padding:0px;
  border-bottom: medium solid lightgray;
  display:flex;
  align-items: center;
  justify-content: center;
  
  img {
    display: block;
    max-width: 70%;
    width: auto;
    height: auto;
    margin: 4% 15% 2% 15%;
    
  }
  
  h1 {
    margin: 0px;
    padding: 8px;
    text-align: center;
    font-size: 1.6em;
    color: #636e72;
  }
  h5 {
    margin: 0px;
    text-align: center;
    font-size: 0.6em;
    color: black;
  }
`
