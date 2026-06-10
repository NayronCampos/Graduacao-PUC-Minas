
import React from 'react';
import { Calendar } from 'lucide-react';
import NavBar from '@/components/NavBar';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Input } from '@/components/ui/input';
import { Checkbox } from '@/components/ui/checkbox';
import { Button } from '@/components/ui/button';

const Cronogramas = () => {
  const weekDays = [
    { id: 'segunda', label: 'Segunda-feira' },
    { id: 'terca', label: 'Terça-feira' },
    { id: 'quarta', label: 'Quarta-feira' },
    { id: 'quinta', label: 'Quinta-feira' },
    { id: 'sexta', label: 'Sexta-feira' },
    { id: 'sabado', label: 'Sábado' },
    { id: 'domingo', label: 'Domingo' },
  ];

  return (
    <div className="min-h-screen flex flex-col bg-gray-100">
      <NavBar />
      <main className="container mx-auto px-4 pt-24 pb-24 flex-1">
        <div className="max-w-2xl mx-auto bg-white rounded-lg shadow-md p-6">
          <h1 className="text-3xl font-bold text-center text-vagacerta-blue mb-8">Cronograma</h1>
          
          <div className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Cargo</label>
              <Select>
                <SelectTrigger>
                  <SelectValue placeholder="Selecione o cargo" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="cargo1">Analista</SelectItem>
                  <SelectItem value="cargo2">Técnico</SelectItem>
                  <SelectItem value="cargo3">Assistente</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Horas de Estudo</label>
              <Input type="number" placeholder="Horas de Estudo" className="w-full" />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Data da Prova</label>
              <div className="relative">
                <Input type="date" className="w-full" />
                <Calendar className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-3">Dias para estudar</label>
              <div className="space-y-2">
                {weekDays.map((day) => (
                  <div key={day.id} className="flex items-center space-x-2">
                    <Checkbox id={day.id} />
                    <label htmlFor={day.id} className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                      {day.label}
                    </label>
                  </div>
                ))}
              </div>
            </div>

            <div className="flex flex-wrap gap-4 pt-4">
              <Button className="flex-1 bg-vagacerta-blue hover:bg-blue-700">
                Criar Cronograma
              </Button>
              <Button variant="outline" className="flex-1">
                Seus Cronogramas
              </Button>
              <Button variant="outline" className="flex-1 text-red-600 hover:text-red-700">
                Fechar Cronogramas
              </Button>
            </div>
          </div>
        </div>
      </main>

      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default Cronogramas;
