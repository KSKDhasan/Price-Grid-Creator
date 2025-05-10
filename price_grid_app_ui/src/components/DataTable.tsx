"use client";
import { useQuery } from '@tanstack/react-query';
import React, { useState } from 'react';
import PriceTable from '@/components/reusablecomponents/PriceTable';
import axiosInstance from '@/lib/axios';
import Skeleton from '@/components/reusablecomponents/Skeleton';
import { Button } from '@/components/ui/button';


interface PriceDataDS {
  id: number,
  width: {
    id: number,
    widthValue: number
  },
  price: number,
  height: {
    id: number,
    heightValue: number
  }
}


// Fetch widths 
const fetchWidths = async () => {
  const response = await axiosInstance.get('/widths');
  console.log(response.data, "widths");

  return response.data; // Assuming this returns a list of width values
};

// Fetch heights 
const fetchHeights = async () => {
  const response = await axiosInstance.get('/heights');
  console.log(response.data, "heights");
  return response.data; // Assuming this returns a list of height values
};


// Fetch Prices
const fetchPrices = async () => {
  const response = await axiosInstance.get('/prices');
  console.log(response.data, "prices");
  return response.data;
};


// Querying Width
const PriceGrid: React.FC = () => {


  // delete state for enable the state
  const [isDelete, setIsDelete] = useState(false);



  const { data: widthData, isLoading: loadingWidths, error: errorWidths } = useQuery({
    queryKey: ['widths'],
    queryFn: fetchWidths,
  });


  // Querying Height
  const { data: heightData, isLoading: loadingHeights, error: errorHeights } = useQuery({
    queryKey: ['heights'],
    queryFn: fetchHeights,
  });


  // Querying 
  const { data: priceData, isLoading: loadingPrices, error: errorPrices } = useQuery({
    queryKey: ['prices'],
    queryFn: fetchPrices,
  });

  console.log(priceData);


  // Handle loading states
  if (loadingWidths || loadingHeights || loadingPrices) return <Skeleton />;
  if (errorWidths || errorHeights || errorPrices) return <div>Failed to load data</div>;


  const widthVals = widthData.map((item: { widthValue: number }) => item.widthValue);
  const heightVals = heightData.map((item: { heightValue: number }) => item.heightValue);

  const priceGrid = heightVals.map((height: number) => {
    return widthVals.map((width: number) => {
      const price = priceData.find(
        (item: PriceDataDS) =>
          item.width.widthValue === width && item.height.heightValue === height
      );
      return price ? price.price : null;
    });
  });


  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4 text-center">Price Grid Creation App</h2>
      <div className='mb-2 flex justify-end gap-10 px-10'>
        {
          isDelete ?
            <Button variant={'outline'} onClick={() => { setIsDelete(false) }} className='bg-blue-100  hover:bg-blue-300 active:bg-blue-100 text-black'>Disable Delete</Button>
            :
            <Button variant={'outline'} onClick={() => { setIsDelete(true) }} className='bg-blue-100  hover:bg-blue-300 active:bg-blue-100 text-black'>Enable Delete</Button>
        }
      </div>
      <PriceTable headers={widthData} rowHeaders={heightData} data={priceGrid} isDelete={isDelete} fetchWidths={fetchWidths} fetchHeights={fetchHeights} fetchPrices={fetchPrices} />
    </div>
  );
};

export default PriceGrid;
