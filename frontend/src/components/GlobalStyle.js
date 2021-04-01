import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  html, body, #root {
    margin: 0;
    padding: 0;
    height: 100%;
    //font-size: 112.5%;
    font-family: "Courier New";
    background-color: var(--seondary-color);
  }
  #root{
    padding: 40px 4vw;
    box-sizing: border-box;
    
    --primary-color: #fdcb6e;
    --secondary-color: moccasin;
    --opacity-hover:0.7;
  }
  
  a{
    text-decoration: none;
    :hover {
      opacity: var(--opacity-hover);
    }
  }
  
`
