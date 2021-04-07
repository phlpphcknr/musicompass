import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  html, body, #root {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    height: 100%;
    //font-size: 112.5%;
    font-family: "Courier New";
    background-color: var(--seondary-color);
    --primary-color: #fdcb6e;
    --secondary-color: moccasin;
    --opacity-hover:0.7;
  }
  
  section{
    padding: 0px;
    margin: 0px;
  }
  
  p{
    font-weight: bold;
  }
  
  h5{
    margin: 0;
    padding: 0;
  }
  
  a{
    text-decoration: none;
    color: chocolate;
    :hover {
      opacity: var(--opacity-hover);
    }
  }
  
  .searchBox#css_custom_input::placeholder{
    font-family: "Courier New";
    font-weight: bold;
    color: black;
  }
  
`
