import Axios from 'axios';
import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/categorystyle.scss'
import Slider from 'react-slick'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";


interface ImageData{
    link: string,
    alt : string
}

interface DataCategory {
    name: string,
    path: string,
    avatar: ImageData
}

interface Response {
    data: any
}

const settings = {
    dots: true,
    infinite: false,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 4,
    initialSlide: 0,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
          infinite: true,
          dots: true
        }
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2,
          initialSlide: 2
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
    ]
};



const CategoryView:React.FC = () => { 
    var [datacategory, setdata] = React.useState<DataCategory[]>()
    React.useEffect(()=>{
            const reponse = async ()=> {
                const result = await Axios.get<Response>(
                "/go/category/");
                setdata(result.data.data)
            };
            reponse();
    },[]);
    return (
            <div className = "row">
                <Slider {...settings}>
                    { 
                        datacategory?.map((i:DataCategory)=>
                            <Link to= {"/category/"+i?.path}>
                                 <div>
                                    <a className="item col-lg-2 col-md-3 col-3 p-4">
                                        <img width = "100%" src={"/cdn/cdn/" +i?.avatar.link} alt = {i?.avatar.alt}/> 
                                        <p className="features-title">{i?.name}</p>
                                    </a>
                                </div>
                            </Link>
                        )
                    }
                </Slider>                
            </div>
        );
}

export default CategoryView;
