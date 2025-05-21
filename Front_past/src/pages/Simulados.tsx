import React, { useState } from 'react';
import NavBar from '@/components/NavBar';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Search } from 'lucide-react';
import { 
  Table, 
  TableBody, 
  TableCell, 
  TableHead, 
  TableHeader, 
  TableRow 
} from '@/components/ui/table';
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

// Mock data for simulados
const mockSimulados = [
  { id: 1, title: "Simulado ENEM Completo", subject: "Geral", questions: 180, date: "10/04/2025" },
  { id: 2, title: "Simulado Concurso Banco do Brasil", subject: "Bancário", questions: 120, date: "15/04/2025" },
  { id: 3, title: "Simulado Polícia Federal", subject: "Segurança", questions: 100, date: "20/04/2025" },
  { id: 4, title: "Simulado Concurso Petrobras", subject: "Engenharia", questions: 80, date: "25/04/2025" },
  { id: 5, title: "Simulado Vestibular USP", subject: "Geral", questions: 90, date: "30/04/2025" },
];

const Simulados: React.FC = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [simulados, setSimulados] = useState(mockSimulados);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      const filtered = mockSimulados.filter(
        item => item.title.toLowerCase().includes(searchQuery.toLowerCase())
      );
      setSimulados(filtered);
    } else {
      setSimulados(mockSimulados);
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      <NavBar />
      
      {/* Main content */}
      <main className="flex-1 pt-20 pb-10">
        {/* Hero section with search */}
        <section className="bg-gray-100 py-16 text-center">
          <div className="container mx-auto px-4">
            <h1 className="text-3xl font-bold mb-8">Busca de Simulados</h1>
            
            <form onSubmit={handleSearch} className="max-w-lg mx-auto flex gap-2">
              <Input
                type="text"
                placeholder="Pesquise por um simulado"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="flex-1"
              />
              <Button type="submit" className="bg-vagacerta-orange hover:bg-orange-500">
                Buscar
              </Button>
            </form>
          </div>
        </section>

        {/* Results section */}
        <section className="container mx-auto px-4 py-12">
          <h2 className="text-2xl font-bold mb-6">Simulados Disponíveis</h2>
          
          <div className="overflow-x-auto rounded-lg border shadow-sm">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Nome do Simulado</TableHead>
                  <TableHead>Área</TableHead>
                  <TableHead>Questões</TableHead>
                  <TableHead>Data</TableHead>
                  <TableHead>Ação</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {simulados.length > 0 ? (
                  simulados.map((simulado) => (
                    <TableRow key={simulado.id}>
                      <TableCell className="font-medium">{simulado.title}</TableCell>
                      <TableCell>{simulado.subject}</TableCell>
                      <TableCell>{simulado.questions}</TableCell>
                      <TableCell>{simulado.date}</TableCell>
                      <TableCell>
                        <Button variant="outline" className="text-vagacerta-blue hover:bg-vagacerta-blue/10">
                          Acessar
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={5} className="text-center py-6">
                      Nenhum simulado encontrado com seus critérios de busca.
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
          
          <div className="mt-8">
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious href="#" />
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#" isActive>1</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">2</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">3</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationNext href="#" />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
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

export default Simulados;
