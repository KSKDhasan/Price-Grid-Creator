"use client";
import React, { useState } from 'react';
import { Plus, Trash } from 'lucide-react';
import { Button } from '@/components/ui/button';
import axiosInstance from '@/lib/axios';
import axios from 'axios';

interface HeaderObject {
  id: number;
  widthValue: number;
}

interface RowHeaderObject {
  id: number;
  heightValue: number;
}

interface PriceTableProps {
  headers: HeaderObject[];
  rowHeaders: RowHeaderObject[];
  data: (string | number)[][];
  isDelete: boolean,
  fetchHeights: () => void,
  fetchWidths: () => void,
  fetchPrices: () => void
}

const PriceTable: React.FC<PriceTableProps> = ({ headers, rowHeaders, data, isDelete, fetchWidths, fetchHeights, fetchPrices }) => {
  const [editedData, setEditedData] = useState<(string | number)[][]>([...data]);
  const [editedHeaders, setEditedHeaders] = useState([...headers]);
  const [editedRowHeaders, setEditedRowHeaders] = useState([...rowHeaders]);

  const [editingCell, setEditingCell] = useState<{ row: number; col: number } | null>(null);
  const [editingColHeader, setEditingColHeader] = useState<number | null>(null);
  const [editingRowHeader, setEditingRowHeader] = useState<number | null>(null);

  const handleCellChange = async (row: number, col: number, value: string) => {
    if (value.trim() === "") {
      const updated = [...editedData];
      updated[row][col] = "";
      setEditedData(updated);
      return;
    }
    if (isNaN(parseFloat(value))) {
      window.alert("Please enter a valid number.");
      return;
    }

    try {
      const updated = [...editedData];
      updated[row][col] = value;
      setEditedData(updated);

      const payload = {
        newPrice: parseFloat(value),
      };

      const rowId = editedRowHeaders[row].id;
      const colId = editedHeaders[col].id;

      const response = await axiosInstance.put(
        `/updatePrice/${rowId}/${colId}?newPrice=${value}`
      );
      console.log(response);
    } catch (error) {
      console.error("Error updating price:", error);
      if (axios.isAxiosError(error)) {
        window.alert(
          `Error updating price: ${error.response?.data?.message || "An unexpected error occurred"}`
        );
      } else {
        window.alert("An unexpected error occurred");
      }
    }
  };

  const handleRowHeaderChange = async (index: number, value: string) => {
    if (value.trim() === "") {
      const updated = [...editedRowHeaders];
      updated[index].heightValue = 0;
      setEditedRowHeaders(updated);
      return;
    }

    try {
      const updated = [...editedRowHeaders];
      updated[index].heightValue = parseFloat(value);
      setEditedRowHeaders(updated);

      const rowId = updated[index].id;

      const response = await axiosInstance.put(`/updateHeight/${rowId}`, {
        heightValue: value,
      });
      console.log(response);
    } catch (error) {
      console.error("Error updating height:", error);
      if (axios.isAxiosError(error)) {
        window.alert(
          `Error updating height: ${error.response?.data?.message || "An unexpected error occurred"}`
        );
      } else {
        window.alert("An unexpected error occurred");
      }
    }
  };

  const handleColHeaderChange = async (index: number, value: string) => {
    if (value.trim() === "") {
      const updated = [...editedHeaders];
      updated[index].widthValue = 0;
      setEditedHeaders(updated);
      return;
    }

    try {
      const updated = [...editedHeaders];
      updated[index].widthValue = parseFloat(value);
      setEditedHeaders(updated);

      const colId = updated[index].id;

      const response = await axiosInstance.put(`/updateWidth/${colId}`, {
        widthValue: value,
      });
      console.log(response);
    } catch (error) {
      console.error("Error updating width:", error);
      if (axios.isAxiosError(error)) {
        window.alert(
          `Error updating width: ${error.response?.data?.message || "An unexpected error occurred"}`
        );
      } else {
        window.alert("An unexpected error occurred");
      }
    }
  };

  const handleAddColumn = async () => {
    try {
      const newColIndex = editedHeaders.length;
      const newId = Math.max(...editedHeaders.map(h => h.id), 0) + 1;

      setEditedHeaders(prev => [...prev, { id: newId, widthValue: 0 }]);
      setEditedData(prev => prev.map(row => [...row, '0']));
      setEditingColHeader(newColIndex);

      const response = await axiosInstance.post(`/addColumn`, null, {
        params: {
          columnIndex: newId + 1,
          rowCount: editedData.length
        }
      });
      console.log(response);
    } catch (error) {
      console.error('Error adding column:', error);
      if (axios.isAxiosError(error)) {
        window.alert(`Error adding column: ${error.response?.data?.message || 'An unexpected error occurred'}`);
      } else {
        window.alert('An unexpected error occurred');
      }
    }
  };

  const handleAddRow = async () => {
    try {
      const newRowIndex = editedData.length;
      const newId = Math.max(...editedRowHeaders.map(h => h.id), 0) + 1;
      const newRow = new Array(editedHeaders.length).fill('0');

      setEditedData(prev => [...prev, newRow]);
      setEditedRowHeaders(prev => [...prev, { id: newId, heightValue: 0 }]);
      setEditingRowHeader(newRowIndex);

      const response = await axiosInstance.post(`/addRow`, null, {
        params: {
          RowIndex: newId+1,
          ColCount: editedData[0].length
        }
      });
      console.log(response);
    } catch (error) {
      console.error('Error adding Row:', error);
      if (axios.isAxiosError(error)) {
        window.alert(`Error adding Row: ${error.response?.data?.message || 'An unexpected error occurred'}`);
      } else {
        window.alert('An unexpected error occurred');
      }
    }
  };

  const handleColumnDelete = async (index: number) => {
    try {
      const colId = editedHeaders[index].id;
      const response = await axiosInstance.delete(`/deleteColum/${colId}`)
      console.log(response);
      setEditedHeaders(prev => prev.filter((_, i) => i !== index));
      setEditedData(prev => prev.map(row => row.filter((_, i) => i !== index)));

    } catch (error) {
      console.error('Error deleting column:', error);
      if (axios.isAxiosError(error)) {
        window.alert(`Error deleting column: ${error.response?.data?.message || 'An unexpected error occurred'}`);
      } else {
        window.alert('An unexpected error occurred');
      }
    }
  }

  const handleRowDelete = async (index: number) => {
    try {
      const rowId = editedRowHeaders[index].id;
      const response = await axiosInstance.delete(`/deleteRow/${rowId}`)
      console.log(response);
      setEditedRowHeaders(prev => prev.filter((_, i) => i !== index));
      setEditedData(prev => prev.filter((_, i) => i !== index));
    } catch (error) {
      console.error('Error deleting row:', error);
      if (axios.isAxiosError(error)) {
        window.alert(`Error deleting row: ${error.response?.data?.message || 'An unexpected error occurred'}`);
      } else {
        window.alert('An unexpected error occurred');
      }
    }
  }

  return (
    <div className="overflow-auto">
      <table className="table-auto border border-gray-600 text-center w-full">
        <thead className="bg-blue-300">
          <tr>
            <th className="border border-gray-300 bg-blue-300 w-10"></th>
            <th className="border border-gray-300 p-3  bg-blue-300">WIDTH TO</th>
            {editedHeaders.map((header, i) => (
              <th key={header.id} className="border border-gray-300 p-2 w-24">
                {editingColHeader === i && !isDelete ? (
                  <input
                    autoFocus
                    type="text"
                    value={header.widthValue}
                    onChange={(e) => handleColHeaderChange(i, e.target.value)}
                    onBlur={() => setEditingColHeader(null)}
                    className="border px-1 w-full"
                  />
                ) : (
                  <div onDoubleClick={() => setEditingColHeader(i)}>{header.widthValue}</div>
                )}
                {
                  isDelete && (
                    <div className='my-2'><Button
                      className="bg-blue-100 hover:bg-blue-200 active:bg-red-100"
                      onClick={() => handleColumnDelete(i)}
                    >
                      <Trash size={10} color="red" />
                    </Button>
                    </div>
                  )
                }
              </th>
            ))}
          </tr>
        </thead>

        <tbody>
          {editedData.map((row, rowIndex) => (
            <tr key={editedRowHeaders[rowIndex].id} className={rowIndex % 2 === 0 ? 'bg-blue-100' : ''}>
              {rowIndex === 0 && (
                <td
                  className="border border-gray-300 font-bold text-center"
                  rowSpan={editedData.length}
                  style={{
                    writingMode: 'vertical-rl',
                    transform: 'rotate(180deg)',
                    padding: '8px',
                    backgroundColor: 'white',
                  }}
                >
                  HEIGHT TO
                </td>
              )}

              <td className={`border border-gray-300 font-semibold p-2 w-24 ${isDelete ? 'flex justify-between items-center' : ''}`}>
                {editingRowHeader === rowIndex && !isDelete ? (
                  <input
                    autoFocus
                    type="text"
                    value={editedRowHeaders[rowIndex].heightValue}
                    onChange={(e) => handleRowHeaderChange(rowIndex, e.target.value)}
                    onBlur={() => setEditingRowHeader(null)}
                    className="border px-1 w-full"
                  />
                ) : (
                  <div onDoubleClick={() => setEditingRowHeader(rowIndex)}>
                    {editedRowHeaders[rowIndex].heightValue}"
                  </div>
                )}
                {
                  isDelete && (
                    <div className='my-2'>
                      <Button
                        className='bg-blue-300 hover:bg-blue-200 active:bg-red-100'
                        onClick={() => handleRowDelete(rowIndex)}
                      >
                        <Trash size={10} color='red' />
                      </Button>
                    </div>
                  )
                }
              </td>

              {row.map((cell, cellIndex) => (
                <td key={editedHeaders[cellIndex].id} className="border border-gray-300 p-2 w-24">
                  {editingCell?.row === rowIndex && editingCell?.col === cellIndex && !isDelete ? (
                    <input
                      autoFocus
                      type="text"
                      value={cell}
                      onChange={(e) => handleCellChange(rowIndex, cellIndex, e.target.value)}
                      onBlur={() => setEditingCell(null)}
                      className="border px-1 w-full"
                    />
                  ) : (
                    <div onDoubleClick={() => setEditingCell({ row: rowIndex, col: cellIndex })}>
                      ${cell}
                    </div>
                  )}
                </td>
              ))}

              {rowIndex === 0 && (
                <td
                  className="border border-gray-300 font-bold text-center"
                  rowSpan={editedData.length}
                  style={{
                    writingMode: 'vertical-rl',
                    transform: 'rotate(360deg)',
                    padding: '8px',
                    backgroundColor: 'white',
                  }}
                >
                  <Button
                    className="w-fit p-3 h-fit"
                    variant="outline"
                    size="icon"
                    onClick={handleAddColumn}
                  >
                    <Plus size={16} />
                  </Button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>

      <div className="flex justify-center border border-gray-300 p-2 ">
        <Button className="w-fit p-3" variant="outline" size="icon" onClick={handleAddRow}>
          <Plus size={16} />
        </Button>
      </div>
    </div>
  );
};

export default PriceTable;