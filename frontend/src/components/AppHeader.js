import styled from 'styled-components/macro'
import {Link} from "react-router-dom";
import logo from "../images/musicompass-logo.png"

export default function AppHeader() {
  return (
      <Header>
          <Link to={"/"}>
              <img src={logo} alt="Logo"/>
          </Link>
      </Header>
  )
}

const Header = styled.header`
  background: var(--primary-color);
  box-shadow: 0px 2px 8px #333;
  border-bottom: medium solid var(--tertiary-color);
  
  img {
    display: block;
    max-width: 70%;
    width: auto;
    height: auto;
    margin: 4% 15% 2% 15%;
  }
`
