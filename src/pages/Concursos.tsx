
import React, { useState } from 'react';
import { Search } from 'lucide-react';
import NavBar from '@/components/NavBar';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Separator } from '@/components/ui/separator';

const Concursos: React.FC = () => {
  const [educationLevel, setEducationLevel] = useState<string>("");
  
  // Dummy data for the example
  const concursos = [
    { id: 1, nome: 'Concurso Público Prefeitura de São Paulo', data: '10/05/2025', nivel: 'Superior', localizacao: 'São Paulo, SP' },
    { id: 2, nome: 'Tribunal Regional Federal', data: '22/06/2025', nivel: 'Superior', localizacao: 'Brasília, DF' },
    { id: 3, nome: 'Banco do Brasil', data: '15/07/2025', nivel: 'Médio', localizacao: 'Nacional' },
  ];
  
  return (
    <div className="min-h-screen flex flex-col">
      <NavBar />
      
      {/* Main Content with padding for the fixed navbar */}
      <main className="flex-1 container mx-auto px-4 pt-24 pb-16">
        {/* Filters Section */}
        <section className="bg-blue-50 rounded-lg p-6 mb-8">
          <h2 className="text-xl font-bold text-vagacerta-blue mb-4">Filtros</h2>
          
          <div className="space-y-4">
            <div>
              <label htmlFor="concurso-nome" className="block font-medium mb-1">Nome do Concurso:</label>
              <Input 
                id="concurso-nome" 
                placeholder="Digite o nome do concurso" 
                className="w-full"
              />
            </div>
            
            <div>
              <label htmlFor="concurso-data" className="block font-medium mb-1">Data:</label>
              <Input 
                id="concurso-data" 
                type="date" 
                placeholder="dd/mm/yyyy" 
                className="w-full"
              />
            </div>
            
            <div>
              <label htmlFor="nivel-ensino" className="block font-medium mb-1">Nível de Ensino:</label>
              <Select value={educationLevel} onValueChange={setEducationLevel}>
                <SelectTrigger id="nivel-ensino" className="w-full">
                  <SelectValue placeholder="Selecione" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="fundamental">Fundamental</SelectItem>
                  <SelectItem value="medio">Médio</SelectItem>
                  <SelectItem value="tecnico">Técnico</SelectItem>
                  <SelectItem value="superior">Superior</SelectItem>
                </SelectContent>
              </Select>
            </div>
            
            <div>
              <label htmlFor="localizacao" className="block font-medium mb-1">Localização:</label>
              <Input 
                id="localizacao" 
                placeholder="Digite a localização" 
                className="w-full"
              />
            </div>
            
            <Button className="w-full md:w-auto mt-2 bg-vagacerta-blue hover:bg-blue-800">
              <Search className="mr-2 h-4 w-4" />
              Pesquisar
            </Button>
          </div>
        </section>
        
        {/* Results Section */}
        <section>
          <h2 className="text-xl font-bold text-vagacerta-blue mb-4">Resultados</h2>
          
          <div className="overflow-x-auto">
            <Table>
              <TableHeader>
                <TableRow className="bg-gray-50">
                  <TableHead className="font-bold">Nome do Concurso</TableHead>
                  <TableHead className="font-bold">Data</TableHead>
                  <TableHead className="font-bold">Nível</TableHead>
                  <TableHead className="font-bold">Localização</TableHead>
                  <TableHead className="w-[100px]"></TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {concursos.map((concurso) => (
                  <TableRow key={concurso.id} className="hover:bg-gray-50">
                    <TableCell className="font-medium">{concurso.nome}</TableCell>
                    <TableCell>{concurso.data}</TableCell>
                    <TableCell>{concurso.nivel}</TableCell>
                    <TableCell>{concurso.localizacao}</TableCell>
                    <TableCell>
                      <Button variant="outline" className="text-blue-600 hover:text-blue-800 hover:bg-blue-50">
                        Detalhes
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </section>
      </main>
      
      {/* Footer */}
      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default Concursos;
